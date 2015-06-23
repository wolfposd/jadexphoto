package photoeffect.filelog;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class FLog
{

    private static Logger _logger = Logger.getLogger("MyLog");

    public static void initLog(String name)
    {
        try
        {

            File logFolder = new File("./logs/");
            if (!logFolder.exists())
            {
                logFolder.mkdir();
            }

            FileHandler filehandler;
            filehandler = new FileHandler("./logs/" + name + "_" + System.currentTimeMillis() + ".log");
            _logger.addHandler(filehandler);
            filehandler.setFormatter(new MyFormatter());

        }
        catch (SecurityException | IOException e)
        {
            e.printStackTrace();
        }

        log("beginning log");

    }

    public static void log(String s)
    {
        _logger.info(s);
    }

    static class MyFormatter extends Formatter
    {

        @Override
        public String format(LogRecord record)
        {
            return record.getMillis() + ";" + record.getThreadID() + ";" + record.getMessage() + "\n";
        }

    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();

        for (Handler h : _logger.getHandlers())
        {
            h.close();
        }
    }

}
