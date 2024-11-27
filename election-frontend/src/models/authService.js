const API_BASE_URL = 'http://localhost:8080/auth';

export async function login(email, password) {
    const loginData = { email, password };

    try {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(loginData),
        });

        if (!response.ok) {
            throw new Error(await response.text());
        }

        return await response.json();
    } catch (error) {
        console.error('Error during login:', error);
        throw error;
    }
}

export async function register(username, email, password) {
    const registerData = { username, email, password };

    try {
        const response = await fetch(`${API_BASE_URL}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(registerData),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText);
        }

        const result = await response.json();
        console.log('Register successful:', result);
        return result;
    } catch (error) {
        console.error('Error during registration:', error);
        throw error;
    }
}
