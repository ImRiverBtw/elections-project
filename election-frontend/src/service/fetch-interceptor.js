import fetchIntercept from 'fetch-intercept';

export class FetchInterceptor {
    static singletonInstance;
    sessionService;
    unregister;

    constructor(sessionService) {
        FetchInterceptor.singletonInstance = this;
        this.sessionService = sessionService;
        this.unregister = fetchIntercept.register(this);
    }

    request(url, options){
        let token = FetchInterceptor.singletonInstance.sessionService.getTokenFromBrowserStorage();

        if (token == null) {
            //no change
            return [url, options];
        } else if (options == null) {
            //authorization header is the only custom option
            return [url, {headers: {'Authorization': token}}];
        } else {
            //add authorization header to other options
            let newOptions = {...options};
            newOptions.headers.Authorization = `Bearer ${token}`;
            return [url, newOptions];
        }
    }

    requestError(error) {
        return Promise.reject(error);
    }
    response(response) {
        // FetchInterceptor.singletonInstance.tryRecoverNewJWToken(response);
        //trap some specific http error responses
        if (response.status >= 400 && response.status < 600) {
            FetchInterceptor.singletonInstance.handleErrorInResponse(response);
        }
        return response;
    }
    responseError(error) {
        return Promise.reject(error);
    }

    handleErrorInResponse(response) {
        console.log(response.status)
        if (response.status >= 500) {
            console.error("Server error occurred. Please try again later.");
        } else if (response.status === 404) {
            console.error("Requested resource not found.");
        } else if (response.status === 403) {
            console.error("You do not have permission to access this resource.");
        } else if (response.status === 401) {
            console.error("Invalid email or password")
        } else if (response.status >= 400) {
            console.error("A client error occurred. Please check your request.");
        }
    }
}