import React from "react";

function E04_2() {
  const imageUrl='logo512.png'
  return (
    <div style={{ textAlign: "center", marginTop: "100px" }}>
      <img src={imageUrl} alt="React Logo" style={{ width: "200px" }} />
      <p style={{ fontStyle: "italic" }}>This is the React logo!</p>
      <p>(I don't know why it's here either)</p>
      <footer>
        <p>The library for web and native user interfaces</p>
      </footer>
    </div>
  );
}

export default E04_2;
