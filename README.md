# Laboratory Report System

## Gereksinimler

- Java 17+
- Maven 3.6+
- PostgreSQL 12+
- Git

## Kurulum Adımları

1. **Kaynak Kodunu Kopyalayın**

    ```sh
    git clone https://github.com/npcBerk/LaboratoryReportSystem.git
    cd LaboratoryReportSystem
    ```

2. **Veritabanı Kurulumu**

   PostgreSQL üzerinde bir veritabanı oluşturun:
   ```sh
   psql -U postgres
    ```

   Sonrasında PostgreSQL şifrenizi girin. Yazdıklarınız komut satırında gözükmeyecek, ama arka planda yazılıyor.

   Şifreyi girdikten sonra aşağıdaki SQL sorgusunu yazabilirsiniz.

   **Not:** Şifre kısmına kendi sunucu şifrenizi giriniz!

    ```sql
    CREATE DATABASE laboratory_report_system;
    CREATE USER postgres WITH ENCRYPTED PASSWORD '/Your Password/';
    GRANT ALL PRIVILEGES ON DATABASE laboratory_report_system TO postgres;
    ```

3. **Yapılandırma Dosyasını Güncelleyin**

   `src/main/resources/application.properties` dosyasını açın ve veritabanı bağlantı bilgilerini güncelleyin:

    ```properties
    spring.application.name=Laboratory Report System

    spring.datasource.url=jdbc:postgresql://localhost:5432/laboratory_report_system
    spring.datasource.username="Your username"
    spring.datasource.password="Your password"

    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

    spring.datasource.hikari.auto-commit=false

    ```

4. **Bağımlılıkları Yükleyin ve Projeyi Derleyin**

    ```sh
    mvnw clean install
    ```

5. **Uygulamayı Çalıştırın**

    ```sh
    mvnw spring-boot:run
    ```
    
6. **HTTP Dosyalarını Kullanarak Veri Kaydedin**

    `src/main/java/com/laboratoryreportsystem` klasöründeki HTTP dosyalarını kullanarak veritabanına veri kaydedebilirsiniz. Bu dosyalar, uygulamanın API isteklerini kolayca test etmenizi sağlar. Örnek dosyalar şunlardır:
    - `CreateLaborant.http`
    - `CreatePatient.http`
    - `CreateReport.http`

## Teknik Seçimler ve Gerekçeleri

### DTO'lar (Data Transfer Objects)
- **Neden Seçildi?**
   - **Veri Taşıma:** Sunucu ile istemci arasındaki veri alışverişini kolaylaştırır.
   - **Soyutlama:** Entity sınıflarını dış dünyadan soyutlayarak veri güvenliğini artırır.

### .http Request Dosyaları
- **Neden Seçildi?**
   - **Kolay Test:** HTTP isteklerini kolayca hazırlayıp test etmeyi sağlar.
   - **Dökümantasyon:** API isteklerinin belgelenmesi ve tekrar kullanılabilirliği sağlar.

### Lombok
- **Neden Seçildi?**
   - **Kodun Okunabilirliği:** Boilerplate kodunu azaltarak daha temiz ve okunabilir kod sağlar.
   - **Kolay Kullanım:** Getter, setter ve constructor gibi sık kullanılan metotları otomatik olarak oluşturur.

### PostgreSQL
- **Neden Seçildi?**
   - **Açık Kaynak:** PostgreSQL, açık kaynaklı bir veritabanıdır ve bu nedenle maliyet etkin bir çözüm sunar.
   - **Kullanım Kolaylığı:** PostgreSQL'in kurulumu ve kullanımı kolaydır.
### Spring Datasource Hikari
- **Neden Seçildi?**
   - **Veri Erişim Yönetimi:** Laborant ve Patient sınıflarının image datasını almaması için `spring.datasource.hikari.auto-commit=false` ayarı yapıldı.

