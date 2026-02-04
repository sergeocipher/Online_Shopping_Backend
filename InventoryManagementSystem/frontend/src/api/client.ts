export const API_BASE = (import.meta.env.VITE_API_BASE as string | undefined) ?? 'http://localhost:8080';

const tokenKey = 'authToken';

export const getAuthToken = () => localStorage.getItem(tokenKey) || undefined;
export const setAuthToken = (token?: string) => {
  if (token) localStorage.setItem(tokenKey, token);
  else localStorage.removeItem(tokenKey);
};

export async function apiFetch<T>(path: string, init?: RequestInit): Promise<T> {
  const response = await fetch(`${API_BASE}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...(init?.headers ?? {}),
      ...(getAuthToken() ? { Authorization: `Bearer ${getAuthToken()}` } : {}),
    },
    ...init,
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || `Request failed with ${response.status}`);
  }

  if (response.status === 204) {
    return undefined as T;
  }

  return (await response.json()) as T;
}
