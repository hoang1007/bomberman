# Bomberman Game

Bài tập lớn số 2 lớp INT2204 1 nhóm 5
Thành viên nhóm Gryffindor:

- Vũ Huy Hoàng - 20020414
- Nguyễn Việt Hoàng - 20020196
- Vũ Hoàng Anh - 20020362

## Tổng quan

Trò chơi Bomberman kinh điển của NES viết bằng java.
Demo: https://www.youtube.com/watch?v=Sqnj3KQgB74

<div float="left">
  <img src="https://user-images.githubusercontent.com/79706035/144868609-048a591b-1527-4ae8-927e-c98994506ca3.png" width="49%" />
  <img src="https://user-images.githubusercontent.com/79706035/144868689-56c43fd7-4de5-4cc7-893f-7c8d913b2e6a.png" width="49%" />
</div>

## Mô tả về đối tượng trong trò chơi

<img src="https://user-images.githubusercontent.com/79706035/144871547-95d2da55-399c-4101-9ab7-73c20b2d1710.png" width="20px" /> <img src="https://user-images.githubusercontent.com/79706035/144871754-4900ac42-f4b3-4f7a-b2c2-b854681d67ab.png" width="20px" /> Bomber là nhân vật chính của trò chơi(white hoặc black), có thể di chuyển theo 4 hướng trái/phải/lên/xuống và đặt bomb theo sự điều khiển của người chơi.

<div>
    <div float = "left" >
    <img src="https://user-images.githubusercontent.com/79706035/144872996-22e4ad0d-f2c6-407e-a115-a99acecd4329.png" width="20px" /><img src="https://user-images.githubusercontent.com/79706035/144873011-a8ae333f-65ff-42f9-8773-004a0f8c344e.png" width="20px" /></br>
    <img src="https://user-images.githubusercontent.com/79706035/144873022-a58fd357-98ec-4490-a223-c0af40c4f38a.png" width="20px" /><img src="https://user-images.githubusercontent.com/79706035/144873033-b7d2c377-7785-4403-9efa-f747dc39f156.png" width="20px" />
    </div>    
    <p>Enemy là các đối tượng mà Bomber phải tiêu diệt để sống sót</p>  
</div>
<div>
<img  src="https://user-images.githubusercontent.com/79706035/144877088-e784bdfa-7045-4452-89cc-8263d7c5a191.png" width="20px" /> Brick có thể bị phá hủy bởi bomb
</div>
<div>
<img src="https://user-images.githubusercontent.com/79706035/144877893-62634620-4032-4cf7-9824-2916cc55799c.png" width="20px" /> Item  đặt dưới Brick và chỉ hiện ra khi Brick bị phá hủy. Môi loại item sẽ tăng thêm sức mạnh cho bomber
</div>
<div>
<img src="https://user-images.githubusercontent.com/79706035/144878547-42bb3d2c-7442-48e3-8f88-30f106b03d43.png" width="20px" /> Portal là nơi Bomber phải đi vào để qua màn
</div>

## Mô tả game play

- Trong một màn chơi, Bomber sẽ được người chơi di chuyển, đặt và kích hoạt Bomb với mục tiêu chính là tiêu diệtEnemy và tìm ra vị trí Portal để có thể qua màn mới
- Bomber sẽ bị giết khi va chạm với Enemy hoặc thuộc phạm vi Bomb nổ
- Enemy bị tiêu diệt khi thuộc phạm vi Bomb nổ
- Vuợt qua tất cả các màn, người chơi sẽ thắng
- Điếm được tính tùy thuộc vào đối tượng b
