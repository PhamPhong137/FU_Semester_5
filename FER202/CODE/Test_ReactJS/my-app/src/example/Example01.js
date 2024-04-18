import React, { Component } from "react";

class Example01 extends Component {
  constructor(props) {
    super(props);
    this.state = { count: 0 };
  }
  componentDidMount() {
    console.log("Component mounted");
  }
  componentDidUpdate(prevState) {
    if (prevState.count !== this.state.count) {
      console.log("count state changed");
    }
  }
  componentWillUnmount() {
    console.log("Component will unmount");
  }
  incrementCount = () => {
    this.setState((prevState) => ({ count: prevState.count + 1 }));
  };
  decrementCount = () => {
    this.setState((prevState) => ({ count: prevState.count - 1 }));
  };
  render() {
    return (
      <div>
        <p>Count: {this.state.count}</p>
        <button onClick={this.incrementCount}>Increment</button>
        <button onClick={this.decrementCount}>Decrement</button>
      </div>
    );
  }
}

export default Example01;
