import React from 'react'

function E04_10() {
  let message;
  const randomNumberPromise = () => {
      return new Promise((resolve, reject) => {
          const randomNumber = Math.floor(Math.random() * 10) + 1; // Số ngẫu nhiên từ 1 đến 10
          if (randomNumber > 5) {
              resolve(randomNumber);
          } else {
              reject('Error: The number is less than or equal to 5');
          }
      })
      .then(number => {
          console.log(number);
      })
      .catch(error => {
         console.log(error);
      });
      
  };
  randomNumberPromise();
  return (
      <div>E04_10</div>
  )
}

export default E04_10

