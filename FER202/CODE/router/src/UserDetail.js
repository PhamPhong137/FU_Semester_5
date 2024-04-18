// UserDetail.js
import React from 'react';
import { useParams } from 'react-router-dom';

const UserDetail = ({ users }) => {
  const { userId } = useParams();
  const user = users[parseInt(userId, 10) - 1];

  return (
    <div>
      <h1>{user.firstName} {user.lastName}</h1>
      <p>Age: {user.age}</p>
    </div>
  );
};

export default UserDetail;
