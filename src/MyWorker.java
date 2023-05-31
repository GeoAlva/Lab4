import javax.swing.SwingWorker;

public class MyWorker extends SwingWorker<String, Integer> {

    public MyWorker() {
        //
    }

    @Override
    protected String doInBackground() throws Exception {
        Thread.sleep(1000);
        return "Done!";
    }

    @Override
    protected void done() {

    }
}
