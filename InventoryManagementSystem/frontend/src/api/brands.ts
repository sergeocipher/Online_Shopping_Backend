import { apiFetch } from './client';
import type { Brand, BrandCreatePayload, BrandUpdatePayload } from '../types';

export async function listBrands(): Promise<Brand[]> {
  return apiFetch<Brand[]>('/api/brands');
}

export async function createBrand(payload: BrandCreatePayload): Promise<Brand> {
  return apiFetch<Brand>('/api/brands', {
    method: 'POST',
    body: JSON.stringify(payload),
  });
}

export async function updateBrand(id: number, payload: BrandUpdatePayload): Promise<Brand> {
  return apiFetch<Brand>(`/api/brands/${id}`, {
    method: 'PUT',
    body: JSON.stringify(payload),
  });
}

export async function deleteBrand(id: number): Promise<void> {
  await apiFetch<void>(`/api/brands/${id}`, { method: 'DELETE' });
}
