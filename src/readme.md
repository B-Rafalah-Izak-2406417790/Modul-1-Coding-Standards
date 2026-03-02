Reflection 1

Prinsip Clean Code & Praktik Secure Coding
Berdasarkan materi pada Module 01, saya telah menerapkan prinsip-prinsip berikut dalam fitur Edit dan Delete:
1. Meaningful Names: Saya memastikan nama variabel dan metode mendeskripsikan tujuannya secara jelas, menghindari misinformasi, serta menggunakan nama yang dapat dicari sesuai standar Clean Code.
2. Functions (Small & Do One Thing): Setiap fungsi dalam Controller dan Service dibuat pendek dan hanya memiliki satu tanggung jawab (Single Responsibility).
3. Objects and Data Structure: Saya menerapkan enkapsulasi dengan menggunakan akses `private` pada daftar data di Repository sehingga data hanya bisa diakses melalui perilaku yang disediakan oleh objek tersebut.
4. Secure Coding (Input Data Validation): Meskipun masih sederhana, penggunaan form binding di Thymeleaf membantu memastikan data dari sisi pengguna dipetakan secara benar ke objek model sebelum diproses lebih lanjut.

Evaluasi dan Rencana Perbaikan
Setelah mempelajari rubrik penilaian dan materi modul, saya mengidentifikasi beberapa kesalahan yang harus diperbaiki:
1. Error Handling: Saat ini aplikasi masih mengembalikan `null` jika data tidak ditemukan. Sesuai prinsip Error Handling, saya seharusnya melemparkan pengecualian (exceptions) yang informatif atau menggunakan `Optional` agar pemanggil fungsi tahu bahwa data tidak ada tanpa menyebabkan program berhenti tiba-tiba.
2. Consistency in Naming: Saya menemukan ketidakkonsistenan antara nama file template (`ListProduct.html`) dan string yang dikembalikan Controller (`productList`). Saya akan menyeragamkan semuanya menggunakan standar penamaan yang konsisten.

Reflection 2

1.  Menulis unit test memberikan rasa aman pada kode yang saya buat. Unit test bertindak sebagai pengaman, memastikan logika inti (seperti `create`, `edit`, `delete`) berjalan sesuai harapan.
    Tidak ada angka pasti untuk jumlah unit test dalam satu kelas. Jumlah tes harus cukup untuk mencakup semua skenario yang bermakna, termasuk:
     Positive Cases: Jalur eksekusi normal.
     Negative Cases: Penanganan error dan input tidak valid.
     Edge Cases: Kondisi batas ekstrem.
    Memiliki 100% code coverage tidak menjamin kode bebas dari bug atau error. Code coverage hanya mengukur persentase baris kode yang dieksekusi selama pengujian.

2.  Membuat kelas functional test baru dengan cara menyalin prosedur setup dan variabel instance dari test suite sebelumnya akan sangat menurunkan kualitas kode. Pendekatan ini menyebabkan duplikasi kode.
    Masalah Clean Code yang Teridentifikasi:
     Duplikasi Kode: Kode konfigurasi (seperti `@Value` dan `@BeforeEach`) ditulis berulang-ulang di setiap file test.
     Kesulitan Pemeliharaan: Jika ada perubahan pada cara setup server, saya harus mengubahnya satu per satu di setiap file test secara manual.
     Keterbacaan: Kelas test menjadi penuh dengan kode konfigurasi sampah (boilerplate) daripada berfokus pada logika pengujian itu sendiri.
    Saran Perbaikan:
    Cara terbaik untuk meningkatkan kebersihan kode adalah dengan menerapkan teknik Inheritance:
     Buat satu kelas dasar (misalnya `BaseFunctionalTest`) yang berisi semua setup umum, injeksi port, dan persiapan variabel URL.
     Ubah `CreateProductFunctionalTest` dan kelas test baru lainnya agar meng-extend kelas dasar tersebut.

Reflection 3

Code Quality Issues dan Strategi Perbaikannya
Selama pengerjaan ini, saya memperbaiki beberapa masalah kode yang dideteksi oleh SonarCloud. Berikut adalah masalah dan strategi perbaikannya:
1. Accessibility pada HTML (Thymeleaf):
SonarCloud mendeteksi bahwa form label tidak terasosiasi dengan control input secara jelas bagi screen reader. Strategi perbaikannya adalah menambahkan atribut for pada tag <label> yang nilainya merujuk langsung pada atribut id dari tag <input> terkait di file createProduct.html dan editProduct.html.

2. Anchor tag sebagai tombol: 
Penggunaan tag <a> untuk Delete menyalahi aturan semantik web, karena tag anchor seharusnya hanya untuk navigasi. Strategi perbaikannya adalah mengganti tag <a> tersebut dengan tag <form> sebaris yang membungkus <button type="submit">, sambil tetap mempertahankan metode GET ke Controller.

3. Empty Methods: 
Terdapat peringatan mengenai metode kosong seperti setUp() pada kelas test. Strategi perbaikannya adalah menghapus metode setUp() karena memang tidak memuat inisialisasi apa pun.

4. Field Injection tidak disarankan: 
Penggunaan @Autowired langsung pada field di ProductController dan ProductServiceImpl memicu Code Smell. Strategi perbaikannya adalah menghapus anotasi tersebut, menjadikan variabelnya final, dan beralih menggunakan pola Constructor Injection.

5. Unnecessary Exceptions & Missing Assertions: 
Terdapat deklarasi exception yang tidak terpakai dan metode pengujian tanpa assertion yang jelas. Strategi perbaikannya adalah menghapus klausa throws Exception yang berlebih pada metode Selenium, serta membungkus pemanggilan method main() dengan assertDoesNotThrow().

Evaluasi Implementasi CI/CD
Berdasarkan workflow dan pipeline yang telah dikonfigurasi, saya yakin implementasi saat ini sudah memenuhi definisi Continuous Integration dan Continuous Deployment:
1. Continuous Integration (CI): 
Proses CI sudah berjalan dengan baik. Setiap kali terdapat aksi push atau pull request, GitHub Actions secara otomatis menjalankan workflow untuk melakukan build, mengeksekusi seluruh unit test dan functional test, serta menganalisis kualitas kode menggunakan SonarCloud dan JaCoCo. Hal ini memastikan bahwa setiap baris kode baru yang masuk selalu terverifikasi kebenaran dan keamanannya secara otomatis.

2. Continuous Deployment (CD): 
Proses CD juga telah terpenuhi melalui integrasi repositori GitHub dengan platform Koyeb. Dengan menggunakan pendekatan pull-based, Koyeb secara otomatis memantau branch main. Ketika kode yang sudah lolos uji CI di-merge ke branch tersebut, Koyeb langsung menarik kode terbaru, membangunnya menggunakan Buildpack, dan merilisnya ke production environment.

Reflection 4
1. Prinsip SOLID yang Diterapkan pada Proyek
Single Responsibility Principle (SRP): 
Saya memisahkan tanggung jawab pembuatan UUID (ID produk/car) dari Repository ke Service. Repository benar-benar murni hanya bertugas mengatur penyimpanan data, sedangkan Service menangani logika. Selain itu, saya memisahkan CarController agar tidak lagi mengurus hal-hal terkait Product.

Open/Closed Principle (OCP): 
Saya memastikan bahwa komponen tingkat tinggi seperti Service dan Controller bergantung pada Interface (seperti ProductRepository dan ProductService). Hal ini membuat aplikasi terbuka untuk perluasan (misal mengganti sistem penyimpanan) tanpa perlu memodifikasi kode inti.

Liskov Substitution Principle (LSP): 
Saya menghapus pewarisan kelas (extends ProductController) pada CarController. Sebuah kelas anak harus bisa menggantikan kelas induknya tanpa merusak fungsionalitas. Memaksakan CarController menjadi anak dari ProductController justru mewariskan endpoint HTTP yang tidak relevan dan berpotensi memunculkan bug.

Interface Segregation Principle (ISP): 
Interface dibuat spesifik dan terpisah. ProductService hanya berisi metode untuk Product, dan CarService hanya untuk Car sehingga tidak dipaksa bergantung pada metode yang tidak mereka gunakan.

Dependency Inversion Principle (DIP): 
Modul tingkat tinggi sekarang bergantung pada abstraksi (Interface). Saya juga mengganti Field Injection (@Autowired pada variabel) dengan Constructor Injection, yang merupakan standar untuk menyuntikkan dependensi dengan aman.

2. Keuntungan Menerapkan Prinsip SOLID beserta Contohnya
Kemudahan ekstensibilitas (OCP & DIP): 
Jika di masa depan saya ingin mengganti penyimpanan data dari ArrayList (in-memory) menjadi basis data PostgreSQL, saya hanya perlu membuat kelas baru yang mengimplementasikan interface ProductRepository. Saya tidak perlu mengubah satu baris pun di dalam file ProductServiceImpl.

Lebih Mudah Diuji (Testability): 
Dengan menggunakan Constructor Injection (DIP), melakukan Unit Testing menjadi sangat mudah karena saya bisa menyuntikkan objek Mock secara manual saat membuat instance dari kelas Service atau Controller di dalam kelas test, tanpa harus menjalankan seluruh kontainer Spring Boot.

Kode Lebih Bersih dan Mudah Dimaintain (SRP): 
Karena ProductRepository sekarang hanya fokus menyimpan dan mencari data, jika terjadi kesalahan pada generate ID, saya tahu persis bahwa saya harus mengecek ProductServiceImpl, bukan Repositorynya.

3. Kerugian Jika Tidak Menerapkan Prinsip SOLID beserta Contohnya
Kode Sangat Kaku dan Rentan Rusak: 
Jika kita melanggar DIP dan bergantung langsung pada implementasi konkrit (seperti sebelum ProductRepository diubah menjadi interface), setiap kali ada perubahan pada cara data disimpan, kita harus merombak kode di Service layer. Ini berisiko merusak logika yang sudah berjalan baik.

Perilaku Sistem Tidak Terduga: 
Saat melanggar LSP dan SRP (seperti saat CarController extends ProductController), endpoint /car/create dan /product/create bisa saling tumpang tindih secara tidak sengaja. Pengguna yang mengakses menu mobil bisa saja tiba-tiba disajikan halaman untuk mengurus produk.

Kesulitan Isolasi Saat Debugging dan Testing: 
Jika melanggar SRP (satu kelas melakukan banyak tugas, seperti Controller yang langsung mengurus akses database sekaligus logika pembuatan UUID), saat muncul error, kita akan kesulitan melacak sumber masalahnya. Proses pembuatan Unit Test juga akan sangat panjang dan rumit karena terlalu banyak dependensi yang harus disiapkan untuk mengetes satu fungsi.