import React from 'react';
import { NavLink } from 'react-router-dom';

const NavigationMenu = () => {
  return (
    <nav>
      <ul>
        <li><NavLink to="/">Trang Chủ</NavLink></li>
        <li><NavLink to="/products">Sản Phẩm</NavLink></li>
        <li><NavLink to="/about">Giới Thiệu</NavLink></li>
        <li><NavLink to="/contact">Liên Hệ</NavLink></li>
        <li><NavLink to="/users/123">Hồ Sơ Người Dùng</NavLink></li>
      </ul>
    </nav>
  );
};

export default NavigationMenu;