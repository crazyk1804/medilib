import React from 'react';
// import logo from './logo.svg';
import './App.css';
import LoginView from "./containers/base/LoginView";
import CssBaseline from "@mui/material/CssBaseline";

function App() {
	return (
		<>
			<CssBaseline/>
			<LoginView/>
		</>
		// <div className="App">
		// 	<header className="App-header">
		// 		<img src={logo} className="App-logo" alt="logo"/>
		// 		<p>
		// 			Edit <code>src/App.tsx</code> and save to reload.
		// 		</p>
		// 		<a
		// 			className="App-link"
		// 			href="https://reactjs.org"
		// 			target="_blank"
		// 			rel="noopener noreferrer"
		// 		>
		// 			Learn React
		// 		</a>
		// 	</header>
		// </div>
	);
}

export default App;
