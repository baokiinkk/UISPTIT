# UISPTIT
![Authors](https://github.com/baokiinkk/paint/blob/master/Authors.png)


### Thực Hiện:
- **Hồ Minh Quốc Bảo** 
- **Trần Nguyễn Chí Nhân** 

### Nội Dung
#### I. Yêu cầu
- **Đăng nhập:**
    - Cho phép người dùng đăng nhập bằng tài khoản do nhà trường cung cấp.Lưu tài khoản cho lần sau mở app.
- **Biểu đồ quá trình học tập:**
    - Show biểu đồ điểm tích lũy của người dùng trong suốt các năm học.
- **Lịch thi, thời khóa biểu, điểm thi:**
    - Show cơ bản về lịch thi, thời khóa biểu, điểm thi ở màn hình chính, người dùng muốn xem chi tiết thì nhấn vào cardView tương ứng.

#### II. Một số kỹ thuật sử dụng
- Android UI/UX Libraries:
    - Sử dụng bộ thư viện từ trang: https://github.com/wasabeef/awesome-android-ui?fbclid=IwAR3pgu1yvdcgd3BCyfZTDMy2JkBVixGCc5o7OiEZ3oTCKLs9a_qTmGeuU6E
- Mô hình MVVM:
    - navigation điều hướng các fragment(viewModel).
    - Koin để tiêm các class(DI).
    - Room database.
    - usercase để chia nhỏ dữ liệu từ repository.
    - flow coroutine + livedata .
    - databinding.
- OKHTTP:
    - request get,post thông tin từ trang uis(trường) để lấy về tập html, sử dụng Jquery để truy vấn lấy các thông tin cần thiết để đưa vào database.
- Các thành phần sử dụng:
    - RecycleView: sử dụng listAdapter để tối ưu, kĩ thuật recycleView lồng recyclView.
    - ViewPage2+TabLayout để thực hiện vuốt các item, hoặc lựa chọn các tab phía dưới.
    - Xử lí đa màn hình.

#### III. Ảnh Demo
![2D](https://github.com/baokiinkk/paint/blob/master/2D.png)

![3D](https://github.com/baokiinkk/paint/blob/master/3D.png)

![Impossible Triangle](https://github.com/baokiinkk/paint/blob/master/ImpossibleTri.png)

![Flower](https://github.com/baokiinkk/paint/blob/master/Flower.png)



 






