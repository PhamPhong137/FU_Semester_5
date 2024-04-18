// UserProfilePage.js
import React from 'react';
import { useParams } from 'react-router-dom';

const UserProfilePage = () => {
  const { userId } = useParams();
  return <h1>Hồ Sơ Người Dùng: {userId}</h1>;
};

export default UserProfilePage;
