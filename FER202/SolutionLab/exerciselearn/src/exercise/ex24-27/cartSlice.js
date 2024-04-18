// features/cart/cartSlice.js
import { createSlice } from '@reduxjs/toolkit';

export const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    items: [],
  },
  reducers: {
    addToCart: (state, action) => {
      const existingIndex = state.items.findIndex(
        (item) => item.id === action.payload.id
      );

      if (existingIndex >= 0) {
        state.items[existingIndex].quantity += 1; // Tăng số lượng nếu sản phẩm đã tồn tại
      } else {
        state.items.push({ ...action.payload, quantity: 1 }); // Thêm mới nếu chưa có
      }
    },
    updateCart: (state, action) => {
      const itemIndex = state.items.findIndex(item => item.id === action.payload.id);
      if (itemIndex >= 0) {
        state.items[itemIndex].quantity = action.payload.quantity; // Cập nhật số lượng
      }
    },
    deleteFromCart: (state, action) => {
      state.items = state.items.filter(item => item.id !== action.payload); // Xoá sản phẩm
    },
  },
});

export const { addToCart, updateCart, deleteFromCart } = cartSlice.actions;
export default cartSlice.reducer;
