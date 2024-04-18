import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import DishList from './DishList';
import DishDetail from './DishDetail';

const E21_2 = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<DishList />} />
        <Route path="/dishes/:dishId" element={<DishDetail />} />
      </Routes>
    </Router>
  );
};

export default E21_2;
