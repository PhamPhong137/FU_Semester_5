// features/products/productSlice.js
import { createSlice } from '@reduxjs/toolkit';

// Giả sử đây là dữ liệu sản phẩm mẫu của bạn
const initialState = {
  productList: [
    {
      id: '123456',
      name: 'Example Product 1',
      price: 9.99,
      description: 'This is an example product 1.',
      catalogs: ['catalog1', 'catalog2'],
    },
    {
      id: '7891011',
      name: 'Example Product 2',
      price: 19.99,
      description: 'This is an example product 2.',
      catalogs: ['catalog3', 'catalog4'],
    },
    // Thêm sản phẩm khác nếu bạn muốn
  ],
};

export const productSlice = createSlice({
  name: 'products',
  initialState,
  reducers: {
    // Reducer để thêm sản phẩm mới vào danh sách
    addProduct: (state, action) => {
      state.productList.push(action.payload);
    },
    // Reducer để xoá sản phẩm bằng ID
    removeProduct: (state, action) => {
      state.productList = state.productList.filter(product => product.id !== action.payload);
    },
    // Reducer để cập nhật thông tin sản phẩm
    updateProduct: (state, action) => {
      const { id, name, price, description, catalogs } = action.payload;
      const existingProduct = state.productList.find(product => product.id === id);
      if (existingProduct) {
        existingProduct.name = name;
        existingProduct.price = price;
        existingProduct.description = description;
        existingProduct.catalogs = catalogs;
      }
    }
  },
});

export const { addProduct, removeProduct, updateProduct } = productSlice.actions;

export default productSlice.reducer;
