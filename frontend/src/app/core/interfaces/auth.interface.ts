export interface AuthenticationRequest {
  email?: string;
  password?: string;
}

export interface AuthenticationResponse {
  accessToken?: string;
  mfaEnabled?: string;
  secretImageUri?: string;
}

export interface RegisterRequest {
  name?: string;
  email?: string;
  password?: string;
  role?: string;
  mfaEnabled?: string;
}

export interface VerificationRequest {
  email?: string;
  code?: string;
}
