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