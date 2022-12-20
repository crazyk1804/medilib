import * as React from 'react';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import imgCity from '../../assets/images/pexels-brayden-law-2096700.jpg';
import {LoginForm} from "../../components/base/login-form/LoginForm";
import {useContext} from "react";
import {GlobalContext} from "../../context/global/GlobalContext";

export default function LoginView() {
	const { login } = useContext(GlobalContext);
	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		const data = new FormData(event.currentTarget);
		const identity = data.get('identity') as string;
		const password = data.get('password') as string;
		if(!identity || !password) {

		}

		login({
			identity: data.get('identity') as string,
			password: data.get('password') as string
		})
		console.log({
			identity, password
		});
	};

	return (
		<Grid container component="main" sx={{height: '100vh'}}>
			{/*<CssBaseline/>*/}
			<Grid
				item
				xs={false}
				sm={4}
				md={7}
				sx={{
					// backgroundImage: 'url(https://source.unsplash.com/random)',
					backgroundImage: `url(${imgCity})`,
					backgroundRepeat: 'no-repeat',
					backgroundColor: (t) =>
						t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
					backgroundSize: 'cover',
					backgroundPosition: 'center',
				}}
			/>
			<Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square
				  sx={{
					  display: 'flex',
					  alignItems: 'center',
					  justifyContent: 'center'
				  }}
			>
				<LoginForm onSubmit={handleSubmit}/>
			</Grid>
		</Grid>
	);
}