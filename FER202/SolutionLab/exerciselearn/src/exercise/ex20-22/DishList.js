import React from 'react';
import { Link } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { dishes } from './dishes'; // Đảm bảo bạn đã import mảng dishes từ file data của bạn

const DishList = () => {
  return (
    <div className="d-flex flex-wrap justify-content-around">
      {dishes.map((dish, index) => (
        <Card key={index} style={{ width: '18rem', margin: '10px' }}>
          <Card.Img variant="top" src={dish.image} alt={dish.name} />
          <Card.Body>
            <Card.Title>{dish.name}</Card.Title>
            <Link to={`/dishes/${dish.id}`}>
              <Button variant="primary">View Details</Button>
            </Link>
          </Card.Body>
        </Card>
      ))}
    </div>
  );
};

export default DishList;
