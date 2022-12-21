import React, {useContext} from 'react';
// import logo from './logo.svg';
import './App.css';
import LoginView from "./containers/base/LoginView";
import CssBaseline from "@mui/material/CssBaseline";
import {GlobalContext, GlobalContextProvider} from "./context/global/GlobalContext";
import {AlertMessage} from "./components/base/messages/Messages";

function App() {
	const { alertOpen, alertTitle, alertMessage, alertClose, loginMember } = useContext(GlobalContext);
	return (
		<>
			<CssBaseline/>
			{!loginMember ?
				<LoginView/> :
				<div>로그인 되었다</div>
			}
			<AlertMessage open={alertOpen} title={alertTitle} message={alertMessage} onClose={alertClose} />
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
