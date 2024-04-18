import React from 'react';
import UserPosts from './UserPosts';

const E13_1 = () => {
  const userId = 2; // For example, you can change this ID to fetch posts for a different user

  return (
    <div>
      <UserPosts userId={userId} />
    </div>
  );
};

export default E13_1;
