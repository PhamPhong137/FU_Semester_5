import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { addToCart, updateCart, deleteFromCart } from './cartSlice';
import { useState } from 'react';

const ProductListE24 = () => {
  const products = useSelector((state) => state.products.productList);
  const dispatch = useDispatch();
  const [quantity, setQuantity] = useState({});

  const handleQuantityChange = (id, value) => {
    setQuantity(prev => ({ ...prev, [id]: value }));
  };
  const handleUpdateClick = (id) => {
    if (!quantity[id]) {
      alert('Please enter a quantity');
      return;
    }
    dispatch(updateCart({ id, quantity: Number(quantity[id]) }));
  };
  return (
    <div>
      {products.map((product) => (
        <div key={product.id}>
          <div>
            <h3>{product.name}</h3>
            <p>Price: ${product.price}</p>
            <p>Description: {product.description}</p>
            <p>Catalogs: {product.catalogs.join(", ")}</p>
          </div>
          <button onClick={() => dispatch(addToCart(product))}>Add to Cart</button>
          <input
            type="number"
            value={quantity[product.id] || ""}
            onChange={(e) => handleQuantityChange(product.id, e.target.value)}
            placeholder="Quantity"
          />
          <button onClick={() => handleUpdateClick(product.id)}>Update Cart</button>
          <button onClick={() => dispatch(deleteFromCart(product.id))}>Delete from Cart</button>
        </div>
      ))}
    </div>
  );
};

export default ProductListE24;
