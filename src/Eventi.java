import java.util.concurrent.ConcurrentHashMap;

public class Eventi {
    private ConcurrentHashMap<String, Integer> mappaEventi = new ConcurrentHashMap<>();

    public void Crea(String nome, Integer posti) {
        synchronized (mappaEventi) {
            mappaEventi.putIfAbsent(nome, posti);
            mappaEventi.notifyAll();
        }
    }

    public boolean Aggiungi(String nome, Integer posti) {
        synchronized (mappaEventi) {
            Integer current = mappaEventi.get(nome);
            long startTime = System.currentTimeMillis();
            while (current == null) {
                try {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long remainingTime = 5000 - elapsedTime;

                    if (remainingTime <= 0) {
                        return false; // Timeout, impossibile aggiungere posti
                    }
                    mappaEventi.wait(remainingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                current = mappaEventi.get(nome);
            }
            mappaEventi.replace(nome, current, current + posti);
            mappaEventi.notifyAll();
            return true;
        }

    }

    public boolean Prenota(String nome, Integer posti) {
        synchronized (mappaEventi) {
            Integer current = mappaEventi.get(nome);
            long startTime = System.currentTimeMillis();
            while (current == null || current - posti < 0) {
                try {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long remainingTime = 5000 - elapsedTime;

                    if (remainingTime <= 0) {
                        return false; // Timeout, impossibile prenotare
                    }

                    mappaEventi.wait(remainingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                current = mappaEventi.get(nome);
            }
            if (current != null && current - posti >= 0) {
                mappaEventi.replace(nome, current, current - posti);
                System.out.println(ListaEventi().toString());
                mappaEventi.notifyAll();
                return true;
            }
            return false;
        }
    }

    public ConcurrentHashMap<String, Integer> ListaEventi() {
        System.out.println(mappaEventi);
        return new ConcurrentHashMap<>(mappaEventi);
    }

    public void Chiudi(String nome) {
        synchronized (mappaEventi) {
            mappaEventi.remove(nome);
            mappaEventi.notifyAll();
        }
    }

    public void Delete() {
        mappaEventi.clear();
    }

    public Integer GetPosti(String nome) {
        return mappaEventi.get(nome);
    }
}
