import {ref} from "vue";

export function useAffiliations() {
    const affiliations = ref([]);
    const err = ref(null);
    const loading = ref(true);

    const fetchAffiliationResults = async () => {
        loading.value = true;
        err.value = null;
        try {
            const response = await fetch('http://localhost:8080/electionresult/affiliation');
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            affiliations.value = await response.json();
        } catch (e) {
            err.value = e;
            console.error(e);
        } finally {
            loading.value = false;
            console.log("test" + err.value)
        }
    };
    return {affiliations, fetchAffiliationResults, err, loading}
}