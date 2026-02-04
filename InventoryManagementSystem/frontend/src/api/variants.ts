import { apiFetch } from './client'
import type { Variant, VariantCreatePayload } from '../types'

export async function listVariants(): Promise<Variant[]> {
  return apiFetch<Variant[]>('/api/variants')
}

export async function createVariant(payload: VariantCreatePayload): Promise<Variant> {
  return apiFetch<Variant>('/api/variants', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}
