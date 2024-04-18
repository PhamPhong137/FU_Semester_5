import React from 'react';
import { BrowserRouter, Routes, Route, Link, Navigate, useNavigate, Outlet } from 'react-router-dom';

// Thành phần Trang Chủ
const Home = () => (
  <div>
    <h1>Trang Chủ</h1>
    <Link to="/about">Đi đến Trang Giới Thiệu</Link>
  </div>
);

// Thành phần Bố cục sử dụng Outlet để hiển thị các thành phần con
const Layout = () => (
  <div>
    <nav>
      <Link to="/">Trang Chủ</Link> | <Link to="/about">Giới Thiệu</Link>
    </nav>
    <main>
      <Outlet /> {/* Các route con sẽ được hiển thị ở đây */}
    </main>
  </div>
);

// Thành phần Giới Thiệu
const About = () => (
  <div>
    <h1>Trang Giới Thiệu</h1>
    <Link to="/about/more">Tìm Hiểu Thêm</Link>
    <Outlet /> {/* Các route con cho /about sẽ được hiển thị ở đây */}
  </div>
);

// Thành phần Thêm thông tin là một route con của Giới Thiệu
const More = () => (
  <div>
    <h2>Thêm Thông Tin Về Chúng Tôi</h2>
    <Link to="/">Đi đến Trang Chủ</Link>
  </div>
);

// Thành phần kích hoạt chuyển hướng
const RedirectToHome = () => {
  const navigate = useNavigate();
  
  React.useEffect(() => {
    navigate('/');
  }, [navigate]);

  return null;
};

// Thành phần App thiết lập router với các route con
const Slide14_12 = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="about" element={<About />}>
          <Route path="more" element={<More />} />
          <Route path="redirect-to-home" element={<Navigate to="/" replace />} />
        </Route>
        {/* Thêm nhiều routes ở đây */}
      </Route>
    </Routes>
  </BrowserRouter>
);

export default Slide14_12;
