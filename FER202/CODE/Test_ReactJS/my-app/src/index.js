import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";


const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
  // <section>
  //   <h2> Array</h2>
  //   <ul> 
  //     {
  //       array.map(i => (<li key={i}>{i}</li>))
  //     }
  //   </ul>
  //   <h2> object</h2>
  //   <ul>
  //     {Object.keys(object).map(i => (<li>{i}: {object[i]} </li>))}
  //   </ul>


  // </section>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
