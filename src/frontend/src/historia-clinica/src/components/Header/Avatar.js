import React from 'react';
import Avatar from 'react-avatar';

const UserAvatar = ({ username }) => {
  const getInitials = (name) => {
    const initials = name
      .split(' ')
      .map((word) => word[0])
      .join('')
      .toUpperCase();
    return initials;
  };

  return (
    <Avatar
      name={getInitials(username)}
      round
      size="60"
      textSizeRatio={2}
    />
  );
};

export default UserAvatar;