import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { addProduct } from './productSlice';

const ProductForm = () => {
  const [product, setProduct] = useState({
    name: '',
    price: '',
    description: '',
    catalogs: ''
  });
  const dispatch = useDispatch();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(addProduct({
      ...product,
      id: Date.now().toString(),
      price: parseFloat(product.price),
      catalogs: product.catalogs.split(',')
    }));
    setProduct({ name: '', price: '', description: '', catalogs: '' });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" name="name" value={product.name} onChange={handleChange} placeholder="Tên sản phẩm" required />
      <input type="number" name="price" value={product.price} onChange={handleChange} placeholder="Giá" required />
      <textarea name="description" value={product.description} onChange={handleChange} placeholder="Mô tả" />
      <input type="text" name="catalogs" value={product.catalogs} onChange={handleChange} placeholder="Catalogs (comma separated)" />
      <button type="submit">Thêm Sản Phẩm</button>
    </form>
  );
};

export default ProductForm;
