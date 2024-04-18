import React from 'react';
import { Route } from 'react-router-dom';
import UserProfilePage from './UserProfilePage';
const HomePage = () => <h1>Trang Chủ</h1>;
const ProductsPage = () => <h1>Trang Sản Phẩm</h1>;
const AboutPage = () => <h1>Trang Giới Thiệu</h1>;
const ContactPage = () => <h1>Trang Liên Hệ</h1>;
export const routes = [
  { path: '/', element: <HomePage />, exact: true },
  { path: '/products', element: <ProductsPage /> },
  { path: '/about', element: <AboutPage /> },
  { path: '/contact', element: <ContactPage /> },
  { path: '/users/:userId', element: <UserProfilePage /> },
];
export const renderRoutes = (routes) => {
  return routes.map((route, index) => (
    <Route key={index} path={route.path} element={route.element} exact={route.exact} />
  ));
};