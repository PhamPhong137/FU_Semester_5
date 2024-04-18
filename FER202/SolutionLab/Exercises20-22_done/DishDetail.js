import React from 'react';
import { useParams } from 'react-router-dom';
import { dishes } from './dishes';

const DishDetail = () => {
  const { dishId } = useParams();
  const dish = dishes.find(d => d.id === parseInt(dishId, 10));

  if (!dish) {
    return <div>Món ăn không tồn tại.</div>;
  }

  return (
    <div>
      <h1>{dish.name}</h1>
      <p>{dish.description}</p>
      <img src={`../${dish.image}`} alt={dish.name} />
      <p>Category: {dish.category}</p>
      <p>Price: ${dish.price}</p>
    </div>
  );
};

export default DishDetail;
