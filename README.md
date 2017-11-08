[![Release](https://jitpack.io/v/jajanjawa/jnowdb.svg)](https://jitpack.io/#jajanjawa/jnowdb)

### Apa ini
RPC diatas [NowDB Relay](http://nowdb.net)

### Keunggulan
* Menggunakan **room id** bukan **public ip**

### Catatan
* Berbasis String, menggunakan banyak data.

### Gradle
```groovy
repositories {
    jcenter()
    maven { url 'https://jitpack.io' }
}
dependencies {
    compile "com.github.jajanjawa:juwet:$juwetVersion"
}
```

### Membuat modul interface
diperlukan oleh server maupun klien.
```java
public interface CoreApi {
    String NAME = "core"; // buat nama modul yang unik
    String salam(String name);
}
```

### Membuat Server
#### buat implementasi modul
```java
public class CoreModule implements CoreApi, JuwetModule {
   @Override
   public String name() {
       return CoreApi.NAME;
   } 
   
   @Override
   public String salam(String name) {
        return "Halo " + name;
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
        coreApi = juwet.create(CoreApi.NAME, CoreApi.class, this);
    }
    
    /**
    * Dipanggil oleh user sendiri
    */
    public void doSalam() {
        coreApi.salam("Budi");
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