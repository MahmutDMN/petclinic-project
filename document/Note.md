# Alınan Notlar
```
* Eclipse TCP/IP monitor kullanimi notlar
    STS de java sekmesine gelinip Window->Show View ->Others->TCP/IP Monitor eklenir.
    Panel altta konsol yanında acılır.
    Acılan panelde sol üst kısmında Show Header aktif edilebilir.
    Altında properties'a girilerek TCP/IP Monitor konfigurasyonları yapılır.
    Properties buttonuna tıkladıktan sonra açılan ekranda Add yapılarak yeni monitor eklenir.
    Local monitoring port bizim izlemek istedigimiz kullanacagımız boşta portu yazarız ben 8085 yazdım.
    Diger alanlar doldurulur
        hostname:localhost
        port:8080
    yazılarak OK yapılır.
    Sonrasında ekledigimiz monitor seçilerek start yapılır.
    Artık postman de monitor ettigimiz 8085 portuna istek gönderdigimizde 
    req ve resp gorebilecegiz.
    Postman cagirilan istek -> http://localhost:8085/rest/owners (Mevcutta 8080 de calısmaktadir.)
    
    

```