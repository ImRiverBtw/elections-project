const API_BASE_URL = 'http://localhost:8080/auth';

export class SessionService {

    constructor(resourcesUrl, browserStorageItemName) {
        this.RESOURCES_URL = resourcesUrl;
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
    }

    async asyncLogIn(email, password) {
        const loginData = {email, password};
        let response = await fetch(`${this.RESOURCES_URL}/auth/login`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(loginData),
            credentials: 'include',
        })
        if (response.ok) {
            this.saveTokenIntoBrowserStorage(
                response.headers.get('Authorization'),
            );
            return true;
        }
        return false;
    }

    saveTokenIntoBrowserStorage(token) {
        if (token == null) {
            const sessionStorageToken = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);

            const localStorageToken = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
            if (localStorageToken === sessionStorageToken) {
                window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
            }
        } else {
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
        }
    }

    getTokenFromBrowserStorage() {
        let token = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);

        if (token == null) {
            token = window.localStorage.getItem(this.RESOURCES_URL);
        }
        return token;
    }

    isAuthenticated() {
        let token = this.getTokenFromBrowserStorage();
        return token != null;
    }
    isAdmin(){
        let token = this.getTokenFromBrowserStorage();
        try{
            let decodedToken = jwt.decode(token);
            return decodedToken.role === "ADMIN";
        } catch(err){
            console.error("error decoding token")
            return false;
        }
    }
}

export async function login(email, password) {
    const loginData = {email, password};
    try {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(loginData),
        });
        if (response.ok) {
            this.saveTokenIntoBrowserStorage(
                response.headers.get('Authorization'),
            );
            return true;
        }
        throw new Error(await response.text());
    } catch (error) {
        console.error('Error during login:', error);
        throw error;
    }
}

export async function register(username, email, password) {
    const registerData = {username, email, password};

    try {
        const response = await fetch(`${API_BASE_URL}/register`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
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
