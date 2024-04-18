// UserList.js
import React from 'react';
import { Link } from 'react-router-dom';

const UserList = ({ users }) => {
  return (
    <ul>
      {users.map((user, index) => (
        <li key={index}>
          <Link to={`/users/${index + 1}`}>{user.firstName} {user.lastName}</Link>
        </li>
      ))}
    </ul>
  );
};

export default UserList;
