import { ref } from 'vue';

export function useAffiliations() {
    const affiliations = ref([]);
    const err = ref(null);
    const loading = ref(false);

    const fetchAffiliationResults = async () => {
        loading.value = true;
        try {
            const response = await fetch('http://localhost:8080/electionresult/votes/affiliation');
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            affiliations.value = data.filter(affiliation => affiliation.seatCount > 0);
        } catch (error) {
            err.value = error;
        } finally {
            loading.value = false;
        }
    };

    return { affiliations, err, loading, fetchAffiliationResults };
}