import React, { Component } from "react";

import { useState, useEffect } from "react";
function Counter() {
  const [count, setCount] = useState(0);

  useEffect(() => {
    console.log("Component mounted or count state changed");
    return () => {
      console.log("Component unmounted or before count state changes");
    };
  }, [count]); // Only re-run the effect if count changes
  return (
    <div>
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>Click me</button>
    </div>
  );
}
export default Counter;
