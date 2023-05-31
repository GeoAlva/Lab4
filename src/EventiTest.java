import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class EventiTest {
    public Eventi eventi = new Eventi();

    @AfterEach
    void teardown() {
        eventi.Delete();
    }

    @Test
    public void initTest() {
        eventi.Crea("Metal", 200);
        assertEquals((Integer) 200, eventi.GetPosti("Metal"));
    }

    @Test
    public void AdminTestCrea() throws InterruptedException {
        Thread admin = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventi.Crea("Classico", 80);
        });
        Thread utente = new Thread(() -> {
            eventi.Prenota("Classico", 80);
        });
        admin.start();
        utente.start();
        admin.join();
        utente.join();
        assertEquals((Integer) 0, eventi.GetPosti("Classico"));
    }

    @Test
    public void AdminTestAggiungi() throws InterruptedException {
        eventi.Crea("Rap", 30);
        Thread admin = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventi.Aggiungi("Rap", 7);
        });
        Thread utente = new Thread(() -> {
            eventi.Prenota("Rap", 33);
        });
        admin.start();
        utente.start();
        admin.join();
        utente.join();
        assertEquals((Integer) 4, eventi.GetPosti("Rap"));
    }

    @Test
    public void AdminTestChiudi() throws InterruptedException {
        eventi.Crea("Pop", 800);
        Thread admin = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventi.Chiudi("Pop");
        });
        Thread utente = new Thread(() -> {
            eventi.Prenota("Pop", 400);
        });

        admin.start();
        utente.start();
        admin.join();
        utente.join();
        assertEquals(null, eventi.GetPosti("Pop"));
    }

    @Test
    public void EventTest() {

        Thread admin = new Thread(() -> {
            try {
                eventi.Crea("Rap", 30);
                Thread.sleep(1000);
                eventi.Aggiungi("Rap", 20);
                Thread.sleep(1000);

                eventi.Crea("Rock", 400);
                Thread.sleep(1000);
                eventi.Aggiungi("Rock", 100);
                Thread.sleep(1000);

                eventi.Crea("Pop", 50);
                Thread.sleep(1000);
                eventi.Aggiungi("Pop", 150);
                Thread.sleep(1000);

                eventi.Crea("Metal", 200);
                Thread.sleep(1000);
                eventi.Aggiungi("Metal", 100);
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("sleep exception");
            }
        });
        Thread user = new Thread(() -> {
            eventi.Prenota("Rap", 10);
            eventi.Prenota("Rap", 20);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventi.Prenota("Pop", 100);
            eventi.Prenota("Metal", 100);

        });
        admin.start();
        user.start();
        try {
            admin.join();
            user.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals((Integer) 20, eventi.GetPosti("Rap"));
        assertEquals((Integer) 100, eventi.GetPosti("Pop"));
        assertEquals((Integer) 200, eventi.GetPosti("Metal"));

    }

}
