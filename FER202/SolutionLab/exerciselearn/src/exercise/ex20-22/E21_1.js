// App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import UserList from './UserList';
import UserDetail from './UserDetail';
const users = [
    { firstName: "John", lastName: "Done", age: 25 },
    { firstName: "Mary", lastName: "Thompson", age: 35 },
    { firstName: "John", lastName: "Smith", age: 30 },
    { firstName: "Emily", lastName: "Johnson", age: 25 },
    { firstName: "William", lastName: "Davis", age: 34 }
];

const E21_1 = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<UserList users={users} />} />
        <Route path="/users/:userId" element={<UserDetail users={users} />} />
      </Routes>
    </Router>
  );
};

export default E21_1;
