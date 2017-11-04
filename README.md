[![Release](https://jitpack.io/v/jajanjawa/jnowdb.svg)](https://jitpack.io/#jajanjawa/jnowdb)

### Apa ini
RPC diatas [NowDB Relay](http://nowdb.net)

### Keunggulan
Menggunakan **room id** bukan **public ip**


### Catatan
* Berbasis String, menggunakan banyak data.
* Room dari NowDB bersifat public, siapapun yang tahu room id, bisa dengarkan pesan.

### Membuat modul interface
diperlukan oleh server maupun klien.
```java
public interface CoreApi {
    String salam();
}
```

### Membuat Server
#### buat implementasi modul
```java
public class CoreModule implements CoreApi, JuwetModule {
   @Override
   public String name() {
       return "core"; // buat nama yang unik
   } 
   
   @Override
   public String salam() {
        return "As Salamu Alaikum";
   }
}
```
#### daftarkan ke server
```java
public class Server {
    private JuwetService service;
    
    public Server() {
        service = new JuwetService("aiueo");
        service.handle(new CoreModule());
    }
}
```

### Buat klien
```java
public class Client {
    
    public Client() {
        juwet = new Juwet("aiueo", "aa iu eo"); // minimal pakai 2 room
        coreApi = juwet.create("core", CoreApi.class, this);
    }
    
    /**
    * Dipanggil oleh user sendiri
    */
    public void doSalam() {
        coreApi.salam();
    }
    
    /**
    * Method untuk terima balasan dari server.
    * Nama method harus sama seperti pada interface, return type dijadikan argument. 
    */
    public void salam(String salam) {
        System.out.println(salam);
        // sampai disini ada waktunya perlu pindah Thread.
        // Platform.runLater atau activity.runOnUiThread
    }
}
```