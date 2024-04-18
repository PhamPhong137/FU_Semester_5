import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavigationMenu from './NavigationMenu';
import { routes, renderRoutes } from './routes';

const E20 = () => {
  return (
    <Router>
      <NavigationMenu />
      <Routes>
        {renderRoutes(routes)}
      </Routes>
    </Router>
  );
};

export default E20;
