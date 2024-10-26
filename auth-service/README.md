- src/
- ├── main/
- │   ├── java/
- │   │   └── com/
- │   │       └── example/
- │   │           └── authentication/
- │   │               ├── config/               # Cấu hình ứng dụng
- │   │               │   ├── SecurityConfig.java - Cấu hình bảo mật, định nghĩa các quy tắc xác thực, bảo vệ các endpoint và cấu hình jwt
- │   │               │   └── WebConfig.java - Cấu hình các yêu cầu HTTP như: CORS nếu cần thiết
- │   │               ├── controller/           # Các controller cho các endpoint API
- │   │               │   └── AuthController.java - Các endpoint API cho đăng nhập và đăng kí user. Sử dụng DTO để nhận dữ liệu từ client và trả về phản hồi
- │   │               ├── domain/               # Miền nghiệp vụ
- │   │               │   └── User.java - Đại diện cho thực thể người dùng
- │   │               ├── dto/                  # Data Transfer Objects
- │   │               │   ├── LoginRequest.java - DTO yêu cầu đăng nhập
- │   │               │   └── RegisterRequest.java - DTO yêu cầu đăng kí
- │   │               ├── exception/             # Xử lý lỗi
- │   │               │   ├── CustomException.java
- │   │               │   └── ErrorResponse.java
- │   │               ├── service/              # Các dịch vụ nghiệp vụ
- │   │               │   └── AuthService.java
- │   │               ├── repository/           # Các repository để tương tác với cơ sở dữ liệu
- │   │               │   └── UserRepository.java
- │   │               └── security/             # Các lớp bảo mật
- │   │                   ├── JwtTokenProvider.java - Chứa logic để tạo và xác thực JWT token
- │   │                   ├── JwtAuthenticationEntryPoint.java - Xử lý các lỗi xác thực không thành công
- │   │                   └── JwtAuthenticationFilter.java - Filter kiểm tra token trong các yêu cầu đến
- │   └── resources/
- │       ├── application.properties            # Cấu hình ứng dụng
- │       └── ...                               # Các tệp khác như template, static resources
- └── test/                                     # Các bài kiểm tra


1. Tổng kết thứ tự triển khai
2. Model (Entity Layer): Định nghĩa dữ liệu của người dùng và quyền.
3. Repository (Data Access Layer): Truy vấn thông tin người dùng.
4. Service Layer: Xử lý logic xác thực và mã hóa.
5. Security Configuration: Cấu hình bảo mật.
6. Controller (API Layer): Cung cấp endpoint cho đăng nhập, đăng ký.


# Các Component của Spring Security

Spring Security là một framework cung cấp các giải pháp bảo mật cho ứng dụng Java, đặc biệt là các ứng dụng web dựa trên Spring. Nó bao gồm nhiều component với các nhiệm vụ và luồng hoạt động khác nhau. Dưới đây là các component chính của Spring Security và nhiệm vụ của chúng:

## 1. Authentication Manager
- **Nhiệm vụ:** Chịu trách nhiệm xác thực người dùng. Khi có một yêu cầu xác thực, Authentication Manager sẽ kiểm tra thông tin đăng nhập (username, password) thông qua các Provider.
- **Luồng hoạt động:** Nhận yêu cầu xác thực từ `AuthenticationProvider`, kiểm tra thông tin người dùng và trả về một đối tượng `Authentication` nếu xác thực thành công.

## 2. Authentication Provider
- **Nhiệm vụ:** Làm nhiệm vụ xác thực cụ thể cho các loại thông tin người dùng khác nhau, như xác thực bằng cơ sở dữ liệu, LDAP, hoặc thông qua API bên ngoài.
- **Luồng hoạt động:** Nhận thông tin đăng nhập từ Authentication Manager, thực hiện kiểm tra và trả về thông tin người dùng hoặc thông báo lỗi.

## 3. UserDetailsService
- **Nhiệm vụ:** Cung cấp thông tin về người dùng. Nó được sử dụng bởi Authentication Provider để lấy thông tin người dùng từ cơ sở dữ liệu hoặc nguồn dữ liệu khác.
- **Luồng hoạt động:** Nhận username từ Authentication Manager và trả về một đối tượng `UserDetails` chứa thông tin chi tiết về người dùng (như quyền hạn, trạng thái).

## 4. Security Context
- **Nhiệm vụ:** Lưu trữ thông tin về người dùng đã được xác thực. Security Context sẽ được sử dụng để kiểm tra quyền truy cập trong suốt quá trình xử lý yêu cầu.
- **Luồng hoạt động:** Khi người dùng được xác thực thành công, thông tin của họ sẽ được lưu vào Security Context. Mỗi yêu cầu HTTP sẽ có Security Context riêng của nó.

## 5. Filter Chain
- **Nhiệm vụ:** Xử lý các yêu cầu HTTP và áp dụng các biện pháp bảo mật (như xác thực, phân quyền).
- **Luồng hoạt động:** Mỗi yêu cầu sẽ đi qua một chuỗi các Filter (như `UsernamePasswordAuthenticationFilter`, `BasicAuthenticationFilter`,...) để kiểm tra và xử lý yêu cầu trước khi đến Controller.

## 6. Access Decision Manager
- **Nhiệm vụ:** Quyết định xem người dùng có quyền truy cập vào tài nguyên hay không.
- **Luồng hoạt động:** Khi một yêu cầu đến, Access Decision Manager sẽ nhận thông tin từ Security Context và xác định quyền truy cập dựa trên các quyền của người dùng và yêu cầu tài nguyên.

## 7. Security Interceptor
- **Nhiệm vụ:** Làm nhiệm vụ can thiệp vào các phương thức trong ứng dụng để kiểm tra quyền truy cập và áp dụng các biện pháp bảo mật.
- **Luồng hoạt động:** Xác minh các yêu cầu vào Controller và đảm bảo rằng người dùng có quyền truy cập trước khi cho phép thực hiện phương thức.

## 8. Session Management
- **Nhiệm vụ:** Quản lý phiên làm việc của người dùng, bao gồm việc bảo vệ chống lại các cuộc tấn công session fixation và điều khiển số lượng phiên làm việc.
- **Luồng hoạt động:** Theo dõi và kiểm soát các phiên của người dùng, đảm bảo rằng mỗi phiên đều hợp lệ và an toàn.

## 9. Exception Handling
- **Nhiệm vụ:** Xử lý các ngoại lệ liên quan đến bảo mật (như lỗi xác thực, truy cập bị từ chối).
- **Luồng hoạt động:** Khi có lỗi xảy ra trong quá trình xác thực hoặc phân quyền, Exception Handling sẽ cung cấp thông báo lỗi thích hợp cho người dùng.

## Luồng hoạt động tổng quát của Spring Security:
1. **Khi người dùng gửi yêu cầu:** Yêu cầu sẽ được gửi tới Filter Chain.
2. **Xác thực người dùng:** Filter xác thực thông tin người dùng thông qua Authentication Manager và Authentication Provider.
3. **Lưu thông tin người dùng:** Nếu xác thực thành công, thông tin người dùng sẽ được lưu trong Security Context.
4. **Kiểm tra quyền truy cập:** Access Decision Manager sẽ kiểm tra quyền của người dùng trước khi cho phép truy cập vào tài nguyên.
5. **Xử lý yêu cầu:** Nếu người dùng có quyền truy cập, yêu cầu sẽ được chuyển đến Controller để xử lý.

Spring Security cung cấp một cơ chế linh hoạt và mạnh mẽ để bảo vệ ứng dụng, từ việc xác thực người dùng đến kiểm soát quyền truy cập và quản lý phiên làm việc.
