import React from "react";
import Login from "./Login";
import OrderList from "./OrderList";
import { BrowserRouter as Router, Link, Routes, Route } from "react-router-dom";
import Addorder from "./Addorder";
import NavbarCustom from "./NavbarCustom";

function App() {
  return (
    <div>
      <Router>
        <NavbarCustom/>
        
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/orders" element={<OrderList />} />
          <Route path="/add-order" element={<Addorder />} />
          <Route path="/login" element={<Login />} />

        </Routes>
      </Router>
    </div>
  );
}

export default App;
