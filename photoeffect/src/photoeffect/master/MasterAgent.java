package photoeffect.master;

import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.future.IFuture;
import jadex.commons.future.ThreadSuspendable;
import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

import measure.generic.IGenericWorkload;
import photoeffect.effect.blur.IEffectBlur;
import photoeffect.effect.mirror.IEffectMirror;
import photoeffect.effect.otherblur.IEffectOtherBlur;
import photoeffect.effect.othermirror.IEffectOtherMirror;
import photoeffect.filelog.FLog;
import photoeffect.lambda.Lambda;

@Description("This agent requires a clock service and a blur service.")
@Agent
@RequiredServices({
        @RequiredService(name = "clockservice", type = IClockService.class, binding = @Binding(scope = RequiredServiceInfo.SCOPE_GLOBAL)),
        @RequiredService(name = "mirrorservice", type = IEffectMirror.class, binding = @Binding(scope = RequiredServiceInfo.SCOPE_GLOBAL)),
        @RequiredService(name = "blurservice", type = IEffectBlur.class, binding = @Binding(scope = RequiredServiceInfo.SCOPE_GLOBAL)),
        @RequiredService(name = "othermirrorservice", type = IEffectOtherMirror.class, binding = @Binding(scope = RequiredServiceInfo.SCOPE_GLOBAL)),
        @RequiredService(name = "otherblurservice", type = IEffectOtherBlur.class, binding = @Binding(scope = RequiredServiceInfo.SCOPE_GLOBAL)) })
public class MasterAgent
{
    @Agent
    MicroAgent agent;

    protected DateFormat format;
    protected MasterAgentGui _gui;

    @AgentBody
    public void executeBody()
    {
        IFuture<IClockService> futureclockservice = agent.getServiceContainer().getRequiredService("clockservice");
        futureclockservice.addResultListener(Lambda.result(clockservice -> {
            System.out.println("Master service starts at: " + new Date(clockservice.getTime()));
        }));

        _gui = new MasterAgentGui(agent.getAgentName(), e -> agent.killComponent());
        _gui.applyButton.addActionListener(e -> applyButtonPressed());

        format = new SimpleDateFormat("hh:mm:ss");

    }

    private void applyButtonPressed()
    {
        String selImage = _gui.imageQueue.getSelectedValue();
        if (selImage == null)
        {
            JOptionPane.showMessageDialog(null, "Select an image", "NO SELECTION!", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            for (int i = 1; i <= 10; i++)
            {
                System.out.println("run " + i + "/10");
                blurImageBlockingCall(_gui.currentImagePanel.getImage());
            }

            System.out.println("DONE DONE DONE!");
        }
    }

    /**
     * Sync version
     */
    private void blurImageBlockingCall(BufferedImage img)
    {
        ThreadSuspendable sus = new ThreadSuspendable();

        for (String serviceName : new String[] { "blurservice", "mirrorservice", "otherblurservice",
                "othermirrorservice" })
        {
            img = callService(img, sus, serviceName);
        }

        if (new Random().nextBoolean())
        {
            System.out.println("entering 2xblur 1xotherblur");
            for (String serviceName : new String[] { "blurservice", "blurservice", "otherblurservice" })
            {
                img = callService(img, sus, serviceName);
            }
        }
        else
        {
            System.out.println("entering 1xmirror 1xothermirror");
            for (String serviceName : new String[] { "mirrorservice", "othermirrorservice" })
            {
                img = callService(img, sus, serviceName);
            }
        }

        System.out.println("changing image in gui");
        _gui.changeImage(img);

    }

    private BufferedImage callService(BufferedImage img, ThreadSuspendable sus, String serviceName)
    {
        FLog.log(this.getClass().getSimpleName() + ";calling " + serviceName);

        @SuppressWarnings("unchecked")
        IGenericWorkload<BufferedImage> service = (IGenericWorkload<BufferedImage>) agent.getServiceContainer()
                .getRequiredService(serviceName).get(sus);

        FLog.log(this.getClass().getSimpleName() + ";modifiying image on " + serviceName);

        img = service.modifyObject(img).get(sus);

        FLog.log(this.getClass().getSimpleName() + ";returning image from " + serviceName);

        return img;
    }

}
