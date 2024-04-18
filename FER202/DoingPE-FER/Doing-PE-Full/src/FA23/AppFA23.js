import React from "react";
import Header from "./Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Star from "./Star";
import Movie from "./Movie";

function AppFA23() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/star" element={<Star />} />
        <Route path="/movie" element={<Movie />} />

      </Routes>
    </Router>
  );
}

export default AppFA23;
