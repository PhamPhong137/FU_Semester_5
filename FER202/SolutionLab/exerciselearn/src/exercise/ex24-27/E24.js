import React from 'react'
import ProductListE24 from './ProductListE24'
import { Provider } from 'react-redux';
import { store } from './store';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Cart from './Cart';
import ProductForm from './ProductForm';
function E24() {
    return (
        <Provider store={store}>
            <Router>
                <nav>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/add-product">Thêm Sản Phẩm</Link></li>
                        <li><Link to="/cart">Giỏ hàng</Link></li>
                    </ul>
                </nav>
                <Routes>
                    <Route path="/" element={<ProductListE24 />} />
                    <Route path="/add-product" element={<ProductForm />} />
                    <Route path="/cart" element={<Cart />} />
                </Routes>
            </Router>
        </Provider>
    )
}

export default E24