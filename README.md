# Spring Thymealef Projesi

### Gerekli Kurulumlar

* JDK 1.8
* Apache Maven
* Docker

## Nasıl Çalıştırılır?

> #### Projenin çalıştırabilmesi için öncelikle docker-compose dosyasının aşağıdaki komutla beraber çalıştırılması gerekmektedir:
* `cd src/main/resources/` 
* `docker-compose -f docker-compose.yml up -d` 
> #### Bu komutla birlikte postgresql veri tabanınız ayağa kalkacaktır.
> #### Ardından uygulamanın çalışır hale gelebilmesi için aşağıdaki kod parçaları çalıştırılmalıdır:
* `cd project-directory/spring-mvc`
* `mvn clean install`
* `mvn spring-boot:run`
> #### Bu komutların ardından uygulama *[localhost:8082](http://localhost:8082/)* portunda çalışır hale gelmektedir.
> Buna ek olarak aşağıdaki komutlar proje içerisindeki testleri de çalıştırır:
* `mvn test` ya da
* `mvn -Dtest=AnimalServiceImplTest,UserServiceImplTest test`


## Uygulama Ne Yapar?
* *[localhost:8082/login](http://localhost:8082/login)* adresinden var olan kullanıcınız ile uygulamaya giriş yapabilirsiniz.
* *[localhost:8082/registration](http://localhost:8082/registration)* adresinden yeni bir kullanıcı yaratabilirsiniz. 
> Not: Yaratılan her yeni kullanıcı ROLE_USER rolü ile yaratılır.
* Sisteme login olduktan sonra *[localhost:8082](http://localhost:8082/)* adresinden size ait olan hayvanları listeleyebilir, yenisini ekleyebilir, arayabilir veyahut güncelleyebilirsiniz.
* Ek olarak admin olan kullanıcılar sistemde var olan hayvanları silebilirler. Bu erişim yalnızca admin kullanıcısına aittir. Ayrıca admin kullanıcıları user bazlı hayvan eklemesi yapabilir.
* Admin kullanıcısı yalnızca db üzerinden yaratılabilir. Bunun için users_roles tablosundan var olan kullanıcınızın id'sini 6 numaraları rol id eşleştirmeniz yeterli olacaktır.
* User rolüne sahip kullanıcılar hayvanların isimlerine göre hayvanlarını aratabilirler
* Admin rolüne sahip kullanıcılar hayvanların isimlerine ve hayvan sahiplerinin isimlerine göre hayvan araması yapabilir.

## Database Yönetim Sistemleri için Database Bağlantısı

* Herhangi bir postgresql destekli database yönetim aracından aşağıdaki ayarları girerek database'e erişim sağlayabilirsiniz.

> (password = benimSifrem)

![image](https://user-images.githubusercontent.com/55767509/162871495-0f43cea4-6260-4b2a-badb-7978d53d1485.png)

## Kullanılan Teknolojiler

#### Thymeleaf

* Thymeleaf, hem web hem de web dışı ortamlarda çalışabilen bir Java XML/XHTML/HTML5 şablon motorudur. *[kaynak](https://en.wikipedia.org/wiki/Thymeleaf)*
* Thymeleaf seçmemdeki amaç, client ve server ortamlarını birleştirerek, daha hızlı bir uygulama ayağa kaldırmaktır.

#### Postgresql

* PostgreSQL ya da Postgres, özgür ve açık kaynak kodlu, SQL destekli bir ilişkisel veritabanı yönetim sistemidir. *[kaynak](https://tr.wikipedia.org/wiki/PostgreSQL)*
* PostgreSQL'i table'lar arası ilişki durumu ve daha önce kullanmış olmamdan dolayı seçtim.

#### Lombok

* Lombok kullanmamdaki amaç, lombok'un sağladığı bazı anatasyonlar ile birlikte kod içerisinde ki kod fazlalığının önüne geçmek.

#### Maven

* Maven, öncelikle Java projeleri için kullanılan bir yapı otomasyon aracıdır. Maven ayrıca C#, Ruby, Scala ve diğer dillerde yazılmış projeleri oluşturmak ve yönetmek için de kullanılabilir. Maven projesi, daha önce Jakarta Projesi'nin bir parçası olduğu Apache Yazılım Vakfı tarafından barındırılıyor. *[kaynak](https://en.wikipedia.org/wiki/Apache_MavenL)*
* Maven seçmemdeki amaç, maven repository ile birlikte bir çok bağımlılığa kolay ulaşabilmem ve kolay yönetilebilir olması.



