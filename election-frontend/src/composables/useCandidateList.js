import { ref } from 'vue';

export function useCandidateList() {
    // Reactive variables to store affiliations, errors, and loading state
    const candidates = ref([]); // Holds the list of affiliations fetched from the server
    const err = ref(null); // Holds any error that occurs during the fetch operation
    const loading = ref(false); // Indicates whether data is currently being fetched

    // Asynchronous function to fetch affiliation results from the API
    const fetchCandidates = async (id) => {
        loading.value = true; // Set loading state to true while fetching data
        try {
            // Make a GET request to the API endpoint
            const response = await fetch(`http://localhost:8080/electionresult/party/${id}/candidates`, {
                method: 'GET'
            });

            // Check if the response status is not OK and throw an error if it isn't
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            // Parse the JSON response
            const data = await response.json();

            // Filter affiliations to include only those with a seat count greater than 0 and sort on votes descending
            candidates.value = data;
        } catch (error) {
            // Capture any error that occurs and assign it to the error state
            err.value = error;
        } finally {
            // Set loading state back to false regardless of success or error
            loading.value = false;
        }
    };

    // Return reactive variables and the fetch function for use in components
    return { candidates, err, loading, fetchCandidates };
}
