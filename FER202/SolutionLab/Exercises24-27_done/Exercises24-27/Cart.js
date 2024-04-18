import React from 'react';
import { useSelector } from 'react-redux';

const Cart = () => {
  const items = useSelector(state => state.cart.items);

  return (
    <div>
      <h2>Giỏ hàng</h2>
      {items.length > 0 ? (
        <ul>
          {items.map(item => (
            <li key={item.id}>{item.name} - {item.quantity} x ${item.price}</li>
          ))}
        </ul>
      ) : (
        <p>Giỏ hàng trống.</p>
      )}
    </div>
  );
};

export default Cart;
